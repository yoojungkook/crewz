document.addEventListener('DOMContentLoaded', function () {
    var photoUploadInput = document.getElementById('photoUpload');
    var fileListContainer = document.getElementById('fileList');
    var uploadButton = document.getElementById('uploadButton');
    var photoModal = new bootstrap.Modal(document.getElementById('photoModal'));

    // 파일 선택 시 파일 목록 표시
    photoUploadInput.addEventListener('change', function (e) {
        fileListContainer.innerHTML = ''; // 기존 목록 초기화
        for (var i = 0; i < e.target.files.length; i++) {
            fileListContainer.innerHTML += '<p>' + e.target.files[i].name + '</p>'; // 파일 이름 표시
        }
    });

    // 사진 업로드 버튼 클릭 시
    uploadButton.addEventListener('click', function () {
        // 업로드 로직 수행

        // 업로드 완료 후 목록 초기화 및 모달 닫기
        fileListContainer.innerHTML = ''; // 파일 목록 초기화
        photoUploadInput.value = ''; // 파일 입력 필드 초기화
        photoModal.hide(); // 모달 창 닫기
    });

    // 모달 창 열릴 때 이전에 업로드한 파일 목록 초기화
    photoModal._element.addEventListener('show.bs.modal', function () {
        fileListContainer.innerHTML = ''; // 파일 목록 초기화
        photoUploadInput.value = ''; // 파일 입력 필드 초기화
    });
});