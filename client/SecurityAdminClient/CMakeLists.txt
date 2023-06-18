set(TARGET_NAME "login")
target_sources("${TARGET_NAME}" PRIVATE
               securityadminwidget.cpp
               userentitywidget.cpp
               securityadminwidget.ui
               userentitywidget.ui
               resource.qrc
               )

target_link_libraries("${TARGET_NAME}"
                      Qt5::Core
                      Qt5::Widgets
                      Qt5::Network
                      )
