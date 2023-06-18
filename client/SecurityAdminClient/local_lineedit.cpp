#include "local_lineedit.h"

Local_LineEdit::Local_LineEdit(QWidget  *parent) : QLineEdit(parent)
{
}

void Local_LineEdit::focusInEvent(QFocusEvent *e){//获取鼠标焦点触发的函数
//    QLineEdit::focusInEvent(e);
    emit(signal_linedit_focussed(true));//发送获取鼠标焦点信号，参数为true
}

void Local_LineEdit::focusOutEvent(QFocusEvent *e){//失去鼠标焦点触发的函数
//    QLineEdit::focusOutEvent(e);
    emit(signal_linedit_focussed(false));//发送获取鼠标焦点信号，参数为false
}

Local_LineEdit::~Local_LineEdit(){

}
