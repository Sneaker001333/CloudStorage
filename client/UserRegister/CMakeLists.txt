set(TARGET_NAME "login")

TARGET_SOURCES("${TARGET_NAME}" PRIVATE
               userregisterwidget.cpp
               )

target_link_libraries("${TARGET_NAME}"
                      Qt5::Core
                      Qt5::Gui
                      Qt5::Network
                      Qt5::Widgets
                      )