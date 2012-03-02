package tss.test;

import org.eclipse.jface.dialogs.IInputValidator;

public class EmailValidator implements IInputValidator{
	public String isValid(String newText) {
		//���������ַ��в���@�ַ�������Ч
		if (newText.indexOf("@")==-1)
			return "������������Ч�ĵ����ʼ���ַ��";
		return null;
	}
}
