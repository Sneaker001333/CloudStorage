#include "auditadminwidget.h"
#include <QApplication>
#include <QFile>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    AuditAdminWidget w;
    w.show();
    QFile file(QString(":/pic/resource/style.qss"));

    file.open(QFile::ReadOnly);

    QString qss = QLatin1String(file.readAll());

    qApp->setStyleSheet(qss);

    return a.exec();
}


