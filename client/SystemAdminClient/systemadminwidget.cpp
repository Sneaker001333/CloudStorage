#include <QJsonParseError>
#include <qdebug.h>
#include <qmap.h>
#include <qvariant.h>
#include <stdio.h>
#include "systemadminwidget.h"
#include "ui_systemadminwidget.h"

SystemAdminWidget::SystemAdminWidget(QWidget *parent)
	:
	QWidget(parent),
	ui(new Ui::SystemAdminWidget)
{
	ui->setupUi(this);
	system_audit();
}

void SystemAdminWidget::system_audit()
{

	if (nullptr == manager) {
		manager = new QNetworkAccessManager(this);
		connect(manager, SIGNAL(finished(QNetworkReply * )),
				this, SLOT(slot_replyFinished(QNetworkReply * )));
		connect(manager, SIGNAL(sslErrors(QNetworkReply * , QList<QSslError>)),
				this, SLOT(slot_sslErrors(QNetworkReply * , QList<QSslError>)));
		connect(manager, SIGNAL(authenticationRequired(QNetworkReply * , QAuthenticator * )),
				this, SLOT(slot_provideAuthenication(QNetworkReply * , QAuthenticator * )));
	}
	QNetworkRequest request;
	QSslConfiguration config;
	config.setPeerVerifyMode(QSslSocket::VerifyNone);
	config.setProtocol(QSsl::TlsV1_2);
	request.setSslConfiguration(config);
	request.setUrl(QUrl("https://81.69.243.226:6500/monitor/health"));
	QString input = username + ":" + password;
	char *inputbyte = (input.toUtf8().data());
	long inputlen;
	long outputlen;
	char *output = nullptr;
	//计算经过base64编码后的字符串长度
	inputlen = strlen((char *)inputbyte);
	if (inputlen % 3 == 0) {
		outputlen = inputlen / 3 * 4;
	}
	else {
		outputlen = (inputlen / 3 + 1) * 4;
	}
	output = (char *)malloc(sizeof(char) * outputlen + 1);
	output[outputlen] = '\0';
	base64enc(output, inputbyte, inputlen);
	QString authorizationstr = QString("Basic ") + QString(output);
	request.setRawHeader("Authorization", authorizationstr.toUtf8());
	post_reply = manager->get(request);

	connect(post_reply, SIGNAL(error(QNetworkReply::NetworkError)),
			this, SLOT(slot_NetWorkError(QNetworkReply::NetworkError)));

}

void SystemAdminWidget::slot_replyFinished(QNetworkReply *reply)
{
	QString ret_data;
	QVariant statusCode = reply->attribute(QNetworkRequest::HttpStatusCodeAttribute);
	if (statusCode.isValid())
		qDebug() << Q_FUNC_INFO << "status code=" << statusCode.toInt();
	QVariantMap details;
	bool ifexist;
	if (nullptr != reply) {
		ret_data = reply->readAll();
		qDebug() << Q_FUNC_INFO << "reply readAll is " << ret_data;

		// 磁盘空间
		if (reply->url().toString().contains("health")) {
			QByteArray resultjsonbytearray;
			QJsonParseError parseresult;
			resultjsonbytearray.append(ret_data);
			QJsonDocument parse_doucment = QJsonDocument::fromJson(resultjsonbytearray, &parseresult);
			if (parseresult.error == QJsonParseError::NoError) {
				if (parse_doucment.isObject()) {
					QVariant total;
					QVariant free;

					details = parse_doucment.toVariant().toMap();
					QMapIterator<QString, QVariant> iterater(details);
					while (iterater.hasNext()) {
						iterater.next();
						QString iteraterkey = iterater.key();
						QVariant iteratervalue = iterater.value();
						if (iteraterkey == "details" || iteraterkey == "diskSpace") {
							details = iteratervalue.toMap();
							iterater = QMapIterator<QString, QVariant>(details);
						}
						else if (iteraterkey == "total") {
							total = iteratervalue;
						}
						else if (iteraterkey == "free") {
							free = iteratervalue;
						}
					}

					ui->listWidget->clear();
					ui->listWidget->addItem("磁盘总空间：" + dataConvert(total.toLongLong()));
					ui->listWidget->addItem("磁盘已使用空间：" + dataConvert(total.toLongLong() - free.toLongLong()));
					ui->listWidget->addItem("磁盘未使用空间：" + dataConvert(free.toLongLong()));
					double percent = 1 - ((double)free.toLongLong()) / total.toLongLong();
					qDebug() << Q_FUNC_INFO << "percent is " << percent;
					ui->progressBar->setValue(percent * 100);
				}
			}
		}
	}
}

void SystemAdminWidget::slot_sslErrors(QNetworkReply *reply, const QList<QSslError> &errors)
{
	QSslCertificate sslcert = errors[0].certificate();
	reply->ignoreSslErrors();
}

void SystemAdminWidget::slot_provideAuthenication(QNetworkReply *reply, QAuthenticator *authenticator)
{

}

void SystemAdminWidget::slot_NetWorkError(QNetworkReply::NetworkError errorCode)
{
	if (nullptr != post_reply) {
		post_reply->deleteLater();
		post_reply = nullptr;
	}
	if (nullptr != manager) {
		delete manager;
		manager = nullptr;
	}
}

SystemAdminWidget::~SystemAdminWidget()
{
	delete ui;
}

void SystemAdminWidget::on_system_management_pushButton_clicked()
{
	ui->main_stackedWidget->setCurrentIndex(0);
	system_audit();
	QStandardItemModel *model = new QStandardItemModel(this);
	QStandardItem *item = new QStandardItem("item1");
	model->appendRow(item);
	item = new QStandardItem("item2");
	model->appendRow(item);
//    ui->listView_2->setModel(model);
}

QString SystemAdminWidget::dataConvert(long long int data)
{
	double result = data;
	QString units[] = {"B", "KB", "MB", "GB", "TB", "PB", "EB"};
	int cnt = 0;
	while (result > 100) {
		result /= 1000;
		cnt++;
	}
	return QString::number(result, 'g', 2) + units[cnt];
}
