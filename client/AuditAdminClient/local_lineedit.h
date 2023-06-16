#ifndef LOCAL_LINEEDIT_H
#define LOCAL_LINEEDIT_H

#include <QObject>
#include <QLineEdit>

class Local_LineEdit : public QLineEdit
{
    Q_OBJECT
public:
    Local_LineEdit(QWidget  *parent = 0);
    ~Local_LineEdit();
signals:
    void signal_linedit_focussed(bool hasFocus);//获取或失去鼠标焦点时发送的信号

public slots:
protected:
  virtual void focusInEvent(QFocusEvent *e);//获取鼠标焦点触发的函数
  virtual void focusOutEvent(QFocusEvent *e);//失去鼠标焦点触发的函数
};

#endif // LOCAL_LINEEDIT_H
