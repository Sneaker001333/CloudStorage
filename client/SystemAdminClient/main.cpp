#include "systemadminwidget.h"
#include <QApplication>
#include <QFile>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    SystemAdminWidget w;
    //    w.show();
    //    Form w;
    w.show();
    QFile file(QString(":/pic/resource/style.qss"));

    file.open(QFile::ReadOnly);

    QString qss = QLatin1String(file.readAll());

    qApp->setStyleSheet(qss);
    return a.exec();
}
