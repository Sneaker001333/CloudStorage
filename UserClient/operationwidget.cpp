#include "operationwidget.h"
#include "ui_operationwidget.h"

OperationWidget::OperationWidget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::OperationWidget)
{
    ui->setupUi(this);
}

OperationWidget::~OperationWidget()
{
    delete ui;
}

void OperationWidget::on_data_open_pushButton_clicked()
{
    emit signal_dataopen();
}

void OperationWidget::on_data_download_pushButton_clicked()
{
    emit signal_datadownload();
}

void OperationWidget::on_data_share_pushButton_clicked()
{
    emit signal_datashare();
}

void OperationWidget::on_data_delete_pushButton_clicked()
{
    emit signal_datadelete();
}
