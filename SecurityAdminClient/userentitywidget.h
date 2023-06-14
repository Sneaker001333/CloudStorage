#ifndef USERENTITYWIDGET_H
#define USERENTITYWIDGET_H

#include <QWidget>
#include <QCheckBox>
#include <QTreeWidgetItem>
#include <QTableWidgetItem>


namespace Ui {
class UserEntityWidget;
}

class UserEntityWidget : public QWidget
{
    Q_OBJECT

public:
    explicit UserEntityWidget(QWidget *parent = nullptr);
    ~UserEntityWidget();



public:
    QString getusername();
    QString getrole();
    QString getstate();
    QString getregistertime();
    QString getforbid_time();
    QTableWidgetItem *getqcheckbox();


    void setusername(QString);
    void setrole(QString);
    void setstate(QString);
    void setregistertime(QString);
    void setforbid_time(QString);
    void setqcheckbox(QTableWidgetItem * );
private:
    Ui::UserEntityWidget *ui;

    QString username;
    QString role;
    QString state;
    QString registertime;
    QString forbid_time;

    QTableWidgetItem *qcheckbox{qcheckbox=nullptr};



};

#endif // USERENTITYWIDGET_H
