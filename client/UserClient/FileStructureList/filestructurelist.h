#ifndef FILESTRUCTURELIST_H
#define FILESTRUCTURELIST_H

#include <QObject>
#include <QTableWidget>
#include <QWidget>
#include <QTreeWidget>
#include <QMap>
#include <QHash>
#include <QMenu>
#include <QSequentialAnimationGroup>
#include <QStandardItemModel>
#include <QTreeWidgetItem>
#include <QUuid>
#include <QFileDialog>

#include <QJsonObject>
#include <QJsonArray>
#include <QJsonDocument>
#include <QJsonParseError>

#include <QFile>

#include <QDebug>
#include <QProcess>
#include <QListWidgetItem>
#include <QThread>
#include <QDebug>
#include <QList>
#include <QtNetwork/QNetworkAccessManager>
#include <QtNetwork/QNetworkRequest>
#include <QtNetwork/QNetworkReply>
#include <QJsonParseError>
#include <QLabel>
#include "global.h"

#include "../entities/localtreewidgetitem.h"
#include "../entities/filemetadata.h"
#include "../transformentity/downloadfileentity.h"
#include "../transformentity/downloadoperation.h"
#include "../transformlistform/downloadlistform.h"
#include "../FileShare/filesharewidget.h"

class FileStructureList : public QObject
{
    Q_OBJECT
public:
    explicit FileStructureList(QObject *parent = nullptr);
    FileStructureList(QTableWidget *tablewidget,
                      QLabel *page_total_num_label,
                      QLabel *metadata_totalnum_label,
                      int current_page,
                      int  page_size,
                      QListWidget *downloadlist_widget,
                      QObject *parent = nullptr);
    FileStructureList(QTableWidget *tablewidget,QString folderuniqueid,int current_page,int  page_size,QObject *parent = nullptr);

    void getfilemetadata(QString folderuniqueid,int current_page,int  page_size);
    QList<FileMetadata*> * parse_json_array(QJsonArray &filelistarray );

    void batch_download();
    void refreshdownloadlistview();
signals:

public slots:
    void slot_replyFinished(QNetworkReply* reply);
    void slot_sslErrors(QNetworkReply *reply, const QList<QSslError> &errors);
    void slot_provideAuthenication(QNetworkReply* reply, QAuthenticator* authenticator);
    void slot_NetWorkError(QNetworkReply::NetworkError);


    void slot_dataopen();
    void slot_datadownload();
    void slot_datashare();
    void slot_datadelete();

//    void  slot_file_share(FileMetadata* );

private:
    QTableWidget *tablewidget;
    QLabel *page_total_num_label;
    QLabel *metadata_totalnum_label;
    QString folderuniqueid;
    int current_page;
    int  page_size;
    QList<FileMetadata*> *filestructure {filestructure=nullptr};
    QNetworkAccessManager *net_manager{ net_manager = nullptr };
    QNetworkReply *post_reply{ post_reply = nullptr };
    QString convert_size(qint64 size);

    QList<FileMetadata*> * filelist{filelist=nullptr};

    QListWidget *downloadlist_widget{downloadlist_widget=nullptr};

    QMap<QString,FileMetadata*> * downloadfilelist{downloadfilelist=nullptr};

};

#endif // FILESTRUCTURELIST_H
