set(TARGET_NAME "secAdminClient")
add_executable("${TARGET_NAME}"
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
