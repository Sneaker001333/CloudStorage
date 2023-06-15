set(TARGET_NAME "login")
target_sources("${TARGET_NAME}" PRIVATE
               auditentity.cpp
               auditadminwidget.cpp
               auditadminwidget.ui
               resource.qrc
               )

target_link_libraries("${TARGET_NAME}"
                      Qt5::Core
                      Qt5::Widgets
                      Qt5::Network
                      )
