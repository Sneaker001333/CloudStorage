#ifndef SEARCHOPERATION_H
#define SEARCHOPERATION_H

#include <QObject>
#include <QFileDialog>
#include <QDebug>
#include <QPainter>
#include <QPropertyAnimation>
#include <QLabel>
#include <QMenu>
#include <QSequentialAnimationGroup>
#include <QStandardItemModel>
#include <QTreeWidgetItem>
#include <QUuid>
#include <QProcess>
#include <QListWidgetItem>
#include <QThread>
class SearchOperation : public QObject
{
    Q_OBJECT
public:
    explicit SearchOperation(QObject *parent = nullptr);

    void data_search(
            QTabWidget tabwidget,
            QString keywords,
            QString type,
            QString mode,
            QDateTime fromtime,
            QDateTime totime);

signals:

public slots:

private:
    QTabWidget tabwidget;
};

#endif // SEARCHOPERATION_H
