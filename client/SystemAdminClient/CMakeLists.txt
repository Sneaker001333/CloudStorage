set(TARGET_NAME "login")
target_sources("${TARGET_NAME}" PRIVATE
               systemadminwidget.cpp
               systemadminwidget.ui
               resource.qrc
               )

target_link_libraries("${TARGET_NAME}"
                      Qt5::Core
                      Qt5::Widgets
                      Qt5::Network
                      )
