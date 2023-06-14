set(TARGET_NAME "userRegister")

add_executable("${TARGET_NAME}"
               local_lineedit.cpp
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
               userregisterwidget.cpp
               main.cpp)

target_link_libraries("${TARGET_NAME}"
                      Qt5::Core
                      Qt5::Gui
                      Qt5::Network
                      Qt5::Widgets
                      )