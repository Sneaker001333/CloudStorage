#include "userentitywidget.h"
#include "ui_userentitywidget.h"

UserEntityWidget::UserEntityWidget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::UserEntityWidget)
{
    ui->setupUi(this);
}


QString UserEntityWidget::getusername(){
    return this->username;
}

QString UserEntityWidget::getrole(){
    return this->role;
}
QString UserEntityWidget::getstate(){
    return this->state;
}
QString UserEntityWidget::getregistertime(){
    return this->registertime;
}
QString UserEntityWidget::getforbid_time(){
    return this->forbid_time;
}
QTableWidgetItem *UserEntityWidget::getqcheckbox(){
    return this->qcheckbox;
}


void UserEntityWidget::setusername(QString username){
    this->username=username;
}
void UserEntityWidget::setrole(QString role){
    this->role=role;
}
void UserEntityWidget::setstate(QString state){
    this->state=state;
}
void UserEntityWidget::setregistertime(QString registertime){
    this->registertime=registertime;
}
void  UserEntityWidget::setforbid_time(QString forbid_time){
    this->forbid_time=forbid_time;
}
void UserEntityWidget::setqcheckbox(QTableWidgetItem * qcheckbox){
    this->qcheckbox=qcheckbox;
}


UserEntityWidget::~UserEntityWidget()
{
    delete ui;
}
