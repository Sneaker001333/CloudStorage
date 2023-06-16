#ifndef SECURITYADMINWIDGET_H
#define SECURITYADMINWIDGET_H

#include <QWidget>
#include "userentitywidget.h"

namespace Ui {
class SecurityAdminWidget;
}

class SecurityAdminWidget : public QWidget
{
    Q_OBJECT

public:
    explicit SecurityAdminWidget(QWidget *parent = 0);
    ~SecurityAdminWidget();
    void securityadmin_userlist();

private slots:
    void on_pushButton_usermanagement_clicked();
    void on_pushButton_operation_audit_clicked();


    void refresh_view();

private:
    Ui::SecurityAdminWidget *ui;
    QMap<QString , UserEntityWidget*> *userentity_list{userentity_list=nullptr};


};

#endif // SECURITYADMINWIDGET_H
