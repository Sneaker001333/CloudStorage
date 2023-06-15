set(TARGET_NAME "login")

add_executable("${TARGET_NAME}"
               loginwidget.cpp
               loginwidget.ui
               ClickableLabel.cpp
               main.cpp
               local_lineedit/local_lineedit.cpp
               OnlineUpdate/onlineupdatedialog.cpp
               OnlineUpdate/onlineupdatedialog.ui
               OnlineUpdate/onlineupdateoperation.cpp
               opensource/sha256.c
               opensource/base64_enc.c
               opensource/hmac_sha256.c
               LocalLog/seccloudlog.cpp
               LocalLog/locallogdialog.cpp
               global.cpp
               resource.qrc
               )

include_directories(${CMAKE_CURRENT_LIST_DIR})

target_link_libraries("${TARGET_NAME}"
                      Qt5::Core
                      Qt5::Widgets
                      Qt5::Network
                      )


