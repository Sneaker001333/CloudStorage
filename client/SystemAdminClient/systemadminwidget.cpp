#include "systemadminwidget.h"
#include "ui_systemadminwidget.h"

SystemAdminWidget::SystemAdminWidget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::SystemAdminWidget)
{
    ui->setupUi(this);


}






void SystemAdminWidget::system_audit(){

    if (nullptr == manager) {
        manager = new QNetworkAccessManager(this);
        connect(manager, SIGNAL(finished(QNetworkReply*)),
                this, SLOT(slot_replyFinished(QNetworkReply*)));
        connect(manager, SIGNAL(sslErrors(QNetworkReply*, QList<QSslError>)),
                this, SLOT(slot_sslErrors(QNetworkReply*, QList<QSslError>)));
        connect(manager, SIGNAL(authenticationRequired(QNetworkReply*, QAuthenticator*)),
                this, SLOT(slot_provideAuthenication(QNetworkReply*, QAuthenticator*)));
    }
    QNetworkRequest request;
    QSslConfiguration config;
    config.setPeerVerifyMode(QSslSocket::VerifyNone);
    config.setProtocol(QSsl::TlsV1_2);
    request.setSslConfiguration(config);
    request.setUrl(QUrl("https://10.10.71.22:9443/monitor/health"));
    QString input = username+":"+password;
    char * inputbyte = (input.toUtf8().data());
    long inputlen;
    long outputlen;
    char *output = nullptr;
    //计算经过base64编码后的字符串长度
    inputlen = strlen((char *)inputbyte);
    if(inputlen % 3 == 0){
        outputlen=inputlen/3*4;
    }
    else{
        outputlen=(inputlen/3+1)*4;
    }
    output=(char *)malloc(sizeof(char)*outputlen+1);
    output[outputlen]='\0';
    base64enc(output,inputbyte,inputlen);
    QString authorizationstr = QString("Basic ")+QString(output);
    request.setRawHeader("Authorization", authorizationstr.toUtf8());
    post_reply = manager->get(request);

    connect(post_reply, SIGNAL(error(QNetworkReply::NetworkError)),
            this, SLOT(slot_NetWorkError(QNetworkReply::NetworkError)));

}



void SystemAdminWidget::slot_replyFinished(QNetworkReply* reply){

    QString ret_data;
    QVariant statusCode = reply->attribute(QNetworkRequest::HttpStatusCodeAttribute);
    if(statusCode.isValid())
        qDebug() <<Q_FUNC_INFO<< "status code=" << statusCode.toInt();
    QString method="";
    QString result="";
    QString code = "";
    QString message = "";
    QVariantMap details;
    bool ifexist;
    if (nullptr != reply) {
        ret_data = reply->readAll();
        qDebug() << Q_FUNC_INFO << "reply readAll is " << ret_data;

    }
}


void SystemAdminWidget::slot_sslErrors(QNetworkReply *reply, const QList<QSslError> &errors) {
    QSslCertificate sslcert = errors[0].certificate();
    reply->ignoreSslErrors();
}

void SystemAdminWidget::slot_provideAuthenication(QNetworkReply* reply, QAuthenticator* authenticator) {

}

void SystemAdminWidget::slot_NetWorkError(QNetworkReply::NetworkError errorCode) {
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
