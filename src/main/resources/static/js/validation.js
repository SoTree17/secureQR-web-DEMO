function formValidCheck() {
    const content = document.QrForm;
    const authURL = content.authUrl.value;
    const data = content.data.value;
    const c_index = content.c_index.value;
    const width = content.width.value;
    const height = content.height.value;


    /*const url_re = new RegExp("https?:\\/\\/[\\w\\-\\.]+/g");
    if (!url_re.test(authURL)) {
        alert("요청 URL 형식을 확인해주세요!");
        return false;
    }*/
    if (!authURL || !data || !c_index || !width || !height) {
        alert("빈칸을 채워주세요!");
        return false;
    } else {
        content.submit();
        alert("이미지가 생성이 되었습니다! 오른쪽에서 확인해주세요")
        return true;
    }
}

function formValidCheck_Register() {
    const content = document.cryptoForm;
    const authURL = content.authUrl.value;
    const token = content.token.value;

    if (!authURL || !token) {
        alert("저희의 QR 코드 라이브러리를 적용한 서버의 주소 또는 토큰 정보 적어주세요!");
        return false;
    } else {
        alert("QR 코드 해싱 및 암호화 방식 추가합니다!")
        return true;
    }
}
