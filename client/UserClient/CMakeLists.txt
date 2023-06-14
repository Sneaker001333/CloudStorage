set(TARGET_NAME "userClient")
add_executable("${TARGET_NAME}"
               FileShare/cancelshare.cpp
               FileShare/datashareentity.cpp
               FileShare/filesharewidget.cpp
               FileShare/getsharedlist.cpp
               FileShare/getsharelist.cpp
               FileStructureList/filestructurelist.cpp
               FolderStructureList/folderstructurelist.cpp
               LocalLog/locallogdialog.cpp
               Login/loginwidget.cpp
               LocalLog/seccloudlog.cpp
               OnlineUpdate/onlineupdatedialog.cpp
               OnlineUpdate/onlineupdateoperation.cpp
               SearchResult/searchoperation.cpp
               entities/filemetadata.cpp
               entities/localtreewidgetitem.cpp
               global.cpp
               main.cpp
               opensource/aes_cbc.c
               opensource/aes_core.c
               opensource/base64_dec.c
               opensource/base64_enc.c
               opensource/des.c
               opensource/hmac_md5.c
               opensource/hmac_sha1.c
               opensource/hmac_sha256.c
               opensource/md5.c
               opensource/rijndeal.c
               opensource/sha1.c
               opensource/sha256.c
               operationwidget.cpp
               securitycloudstorageclientwidget.cpp
               SearchResult/searchresultwidget.cpp
               local_lineedit/local_lineedit.cpp
               transformentity/downloadfileentity.cpp
               transformentity/downloadoperation.cpp
               transformentity/uploadfileentity.cpp
               transformentity/uploadoperation.cpp
               transformlistform/downloadlistform.cpp
               transformlistform/transformlistform.cpp
               transformlistform/uploadlistform.cpp
               tst_testname.cpp
               FileShare/filesharewidget.ui
               LocalLog/locallogdialog.ui
               Login/loginwidget.ui
               OnlineUpdate/onlineupdatedialog.ui
               operationwidget.ui
               securitycloudstorageclientwidget.ui
               SearchResult/searchresultwidget.ui
               transformlistform/downloadlistform.ui
               transformlistform/transformlistform.ui
               transformlistform/uploadlistform.ui
               resource.qrc
               Login/ClickableLabel.cpp
               )

include_directories(Login)

target_link_libraries("${TARGET_NAME}"
                      Qt5::Core
                      Qt5::Widgets
                      Qt5::Network
                      Qt5::Test
                      ${CMAKE_CURRENT_LIST_DIR}/lib/libYunLock.a
                      ${CMAKE_CURRENT_LIST_DIR}/lib/libCipherIndexLib.a
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