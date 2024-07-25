console.log("a");

document.querySelector("#img_file_input").addEventListener('change',function(event){
    var file = event.target.files[0]
    var reader = new FileReader();
    reader.onload = function(event) {
        document.getElementById("upload_img_preview").src = reader.result
    }
    reader.readAsDataURL(file);
})

