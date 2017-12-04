package cn.nchu.lims.exception;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="��Ҫ��ѯ���ļ�������")
public class EnclosureNotFoundException extends IOException {

	private static final long serialVersionUID = 1L;

}
