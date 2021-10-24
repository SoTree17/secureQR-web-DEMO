let status = false;

function formValidCheck() {
    const content = document.QrForm;
    const authURL = content.authUrl.value;
    const data = content.data.value;
    const c_index = content.c_index.value;
    const width = content.width.value;
    const height = content.height.value;

    if (!authURL || !data || !c_index || !width || !height) {
        alert("빈칸을 채워주세요.");
        return false;
    } else {
        content.submit();
        setTimeout(function(){
            alert("이미지 생성이 되었습니다! 오른쪽 이미지 버튼을 눌러 확인해주세요")
            imgChange();
        },1000);
        return true;
    }
}
