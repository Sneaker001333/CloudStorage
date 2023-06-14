#include "securityadminwidget.h"
#include "ui_securityadminwidget.h"

SecurityAdminWidget::SecurityAdminWidget(QWidget *parent)
	:
	QWidget(parent),
	ui(new Ui::SecurityAdminWidget)
{
	ui->setupUi(this);

	ui->normaluser_tableWidget->setSortingEnabled(true);
	ui->normaluser_tableWidget->setSelectionBehavior(QAbstractItemView::SelectRows); //设置选择行为，以行为单位
	ui->normaluser_tableWidget->setSelectionMode(QAbstractItemView::SingleSelection); //设置选择模式，选择单行
	//    ui->normaluser_tableWidget->sortItems(7);
	ui->normaluser_tableWidget->setEditTriggers(QAbstractItemView::NoEditTriggers);//表格不可编辑
	securityadmin_userlist();
	refresh_view();
}

void SecurityAdminWidget::securityadmin_userlist()
{
	QString retdate = "";
	if (nullptr != userentity_list) {
		QMapIterator<QString, UserEntityWidget *> return_result_iterater(*userentity_list);
		if (userentity_list->size() > 0) {
			while (return_result_iterater.hasNext()) {
				return_result_iterater.next();
				QString iteraterkey = return_result_iterater.key();
				UserEntityWidget *return_result_value = return_result_iterater.value();
				delete return_result_value;
				return_result_value = nullptr;
			}
		}
		delete userentity_list;
		userentity_list = nullptr;
	}

	if (nullptr == userentity_list) {
		userentity_list = new QMap<QString, UserEntityWidget *>();
	}

	//发送获取用户标识信息并解析
}

void SecurityAdminWidget::refresh_view()
{
	ui->normaluser_tableWidget->setRowCount(0);
	ui->normaluser_tableWidget->setRowCount(userentity_list->size());
	int addrow = 0;
	if (nullptr != userentity_list) {
		QMapIterator<QString, UserEntityWidget *> iterater(*userentity_list);
		while (iterater.hasNext()) {
			iterater.next();
			UserEntityWidget *list_result_value = iterater.value();
			ui->normaluser_tableWidget->setItem(addrow, 0, list_result_value->getqcheckbox());
			ui->normaluser_tableWidget->setCellWidget(addrow, 1, list_result_value);
			QTableWidgetItem *username = new QTableWidgetItem(list_result_value->getusername());
			ui->normaluser_tableWidget->setItem(addrow, 2, username);
			QTableWidgetItem *role = new QTableWidgetItem(list_result_value->getrole());
			ui->normaluser_tableWidget->setItem(addrow, 3, role);

			QTableWidgetItem *state = new QTableWidgetItem(list_result_value->getstate());
			ui->normaluser_tableWidget->setItem(addrow, 4, state);


			QTableWidgetItem *registertime = new QTableWidgetItem(list_result_value->getregistertime());
			ui->normaluser_tableWidget->setItem(addrow, 5, registertime);

			ui->normaluser_tableWidget->setColumnWidth(0, 40);  //设置列宽
			ui->normaluser_tableWidget->setColumnWidth(1, 100);
			ui->normaluser_tableWidget->setColumnWidth(2, 130);
			ui->normaluser_tableWidget->setColumnWidth(3, 120);
			ui->normaluser_tableWidget->setColumnWidth(4, 120);
			ui->normaluser_tableWidget->setColumnWidth(5, 200);
			ui->normaluser_tableWidget->setRowHeight(addrow, 38);
			addrow++;
		}
	}
}

SecurityAdminWidget::~SecurityAdminWidget()
{
	delete ui;
}

void SecurityAdminWidget::on_pushButton_usermanagement_clicked()
{
	ui->stackedWidget->setCurrentIndex(1);
	ui->pushButton_usermanagement->
		setStyleSheet(
		"QPushButton#pushButton_usermanagement{"
		"border-radius:0px;"
		"background-image: url(:/pic/resource/img/titlebarimg/yonghu_en.png);"
		"}"
		"QPushButton#pushButton_usermanagement:hover{"
		"background-image: url(:/pic/resource/img/titlebarimg/yonghu_hua.png);"
		"}"
		"QPushButton#pushButton_usermanagement:pressed{"
		"background-image: url(:/pic/resource/img/titlebarimg/yonghu_en.png);"
		"}"
	);
	ui->pushButton_operation_audit->
		setStyleSheet(
		"QPushButton#pushButton_operation_audit{"
		"border-radius:0px;"
		"background-image: url(:/pic/resource/img/titlebarimg/shenji_mo.png);"
		"}"
		"QPushButton#pushButton_operation_audit:hover{"
		"background-image: url(:/pic/resource/img/titlebarimg/shenji_hua.png);"
		"}"
		"QPushButton#pushButton_operation_audit:pressed{"
		"background-image: url(:/pic/resource/img/titlebarimg/shenji_en.png);"
		"}"
	);
}

void SecurityAdminWidget::on_pushButton_operation_audit_clicked()
{
	ui->stackedWidget->setCurrentIndex(0);

	ui->pushButton_usermanagement->
		setStyleSheet(
		"QPushButton#pushButton_usermanagement{border-radius:0px;background-image: url(:/pic/resource/img/titlebarimg/yonghu_mo.png);}QPushButton#pushButton_usermanagement:hover{background-image: url(:/pic/resource/img/titlebarimg/yonghu_hua.png);}QPushButton#pushButton_usermanagement:pressed{background-image: url(:/pic/resource/img/titlebarimg/yonghu_en.png);}"
		//                "background-image: url(:/pic/resource/img/btn3/titlebar/yonghu_mo.png);"

	);
	ui->pushButton_operation_audit->
		setStyleSheet(
		"QPushButton#pushButton_operation_audit{border-radius:0px;background-image: url(:/pic/resource/img/titlebarimg/shenji_en.png);}QPushButton#pushButton_operation_audit:hover{background-image: url(:/pic/resource/img/titlebarimg/shenji_hua.png);}QPushButton#pushButton_operation_audit:pressed{background-image: url(:/pic/resource/img/titlebarimg/shenji_en.png);}"
		//                "background-image: url(:/pic/resource/img/btn3/titlebar/shenji_en.png);"
	);
}
