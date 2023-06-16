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
               opensource/base64_dec.c
               opensource/hmac_sha256.c
               opensource/aes_core.c
               opensource/aes_cbc.c
               LocalLog/seccloudlog.cpp
               LocalLog/locallogdialog.cpp
               global.cpp
               resource.qrc
               )

include_directories(${CMAKE_CURRENT_LIST_DIR})
include_directories(${CMAKE_CURRENT_LIST_DIR}/inc)
include_directories(${CMAKE_CURRENT_LIST_DIR}/local_lineedit)
include_directories(../UserClient)
include_directories(../AuditAdminClient)
include_directories(../UserRegister)
include_directories(../SystemAdminClient)

target_link_libraries("${TARGET_NAME}"
                      Qt5::Core
                      Qt5::Widgets
                      Qt5::Network
                      )


add_custom_command(TARGET "${TARGET_NAME}" POST_BUILD
                   COMMAND ${CMAKE_COMMAND} -E copy_directory
                   "${CMAKE_CURRENT_LIST_DIR}/doctotext"
                   #                   "$<TARGET_FILE_DIR:${PROJECT_NAME}>/doctotext"
                   "${CMAKE_CURRENT_BINARY_DIR}/doctotext"
                   COMMAND_EXPAND_LISTS
                   )

add_custom_command(TARGET "${TARGET_NAME}" POST_BUILD
                   COMMAND ${CMAKE_COMMAND} -E copy_directory
                   "${CMAKE_CURRENT_LIST_DIR}/dict"
                   #                   "$<TARGET_FILE_DIR:${PROJECT_NAME}>/dict"
                   "${CMAKE_CURRENT_BINARY_DIR}/dict"
                   COMMAND_EXPAND_LISTS
                   )

add_custom_command(TARGET "${TARGET_NAME}" POST_BUILD
                   COMMAND ${CMAKE_COMMAND} -E copy
                   "${CMAKE_CURRENT_LIST_DIR}/lib/CipherIndexLib.dll"
                   "${CMAKE_CURRENT_LIST_DIR}/lib/YunLock.dll"
                   #                   "$<TARGET_FILE_DIR:${PROJECT_NAME}>/"
                   "${CMAKE_CURRENT_BINARY_DIR}/"
                   COMMAND_EXPAND_LISTS
                   )
