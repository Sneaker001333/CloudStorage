#-------------------------------------------------
#
# Project created by QtCreator 2019-10-24T09:42:07
#
#-------------------------------------------------

QT       += core gui network

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = AuditAdminClient
TEMPLATE = app

# The following define makes your compiler emit warnings if you use
# any feature of Qt which has been marked as deprecated (the exact warnings
# depend on your compiler). Please consult the documentation of the
# deprecated API in order to know how to port your code away from it.
DEFINES += QT_DEPRECATED_WARNINGS

# You can also make your code fail to compile if you use deprecated APIs.
# In order to do so, uncomment the following line.
# You can also select to disable deprecated APIs only up to a certain version of Qt.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0

CONFIG += c++11

SOURCES += \
        auditentity.cpp \
        global.cpp \
        local_lineedit.cpp \
        main.cpp \
        auditadminwidget.cpp \
        opensource/aes_cbc.c \
        opensource/aes_core.c \
        opensource/base64_dec.c \
        opensource/base64_enc.c \
        opensource/des.c \
        opensource/hmac_md5.c \
        opensource/hmac_sha1.c \
        opensource/hmac_sha256.c \
        opensource/md5.c \
        opensource/rijndeal.c \
        opensource/sha1.c \
        opensource/sha256.c

HEADERS += \
        auditadminwidget.h \
        auditentity.h \
        global.h \
        inc/aes_cbc.h \
        inc/alg_err.h \
        inc/base64_dec.h \
        inc/base64_enc.h \
        inc/crypt.h \
        inc/des.h \
        inc/hmac_md5.h \
        inc/hmac_sha1.h \
        inc/hmac_sha256.h \
        inc/md5.h \
        inc/rijndael.h \
        inc/sha1.h \
        inc/sha256.h \
        local_lineedit.h

FORMS += \
        auditadminwidget.ui

# Default rules for deployment.
qnx: target.path = /tmp/$${TARGET}/bin
else: unix:!android: target.path = /opt/$${TARGET}/bin
!isEmpty(target.path): INSTALLS += target

RESOURCES += \
    resource.qrc
