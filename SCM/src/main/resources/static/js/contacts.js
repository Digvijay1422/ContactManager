
// const $modalElement = document.querySelector('#modalEl');

// const modalOptions = {
//     placement: 'bottom-right',
//     backdrop: 'dynamic',
//     backdropClasses:
//         'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
//     closable: true,
//     onHide: () => {
//         console.log('modal is hidden');
//     },
//     onShow: () => {
//         console.log('modal is shown');
//     },
//     onToggle: () => {
//         console.log('modal has been toggled');
//     },
// };

// // instance options object
// const instanceOptions = {
//   id: 'modalEl',
//   override: true
// };

// let modal = new Modal($modalElement, modalOptions, instanceOptions);
// function openContactModal()
// {

//     modal.show();
// }


const baseUrl = "http://localhost:8080"
//delete contact

 function deleteContact(id)
{
    Swal.fire({
        title: "Do you want to delete this contact??",
        icon:"worning",
        showCancelButton: true,
        confirmButtonText: "Delete"
       
      }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
          const url = `${baseUrl}/user/contacts/delete/`+id;
          window.location.replace(url);
        } 
      });   
}


async function loadContactdata(id) {
  //function call to load data
  console.log(id);
  console.log("data");
  try {
    const data = await (await fetch(`${baseUrl}/api/contacts/${id}`)).json();
    console.log(data);
    // document.querySelector("#contact_name").innerHTML = data.name;
    // document.querySelector("#contact_email").innerHTML = data.email;
    // document.querySelector("#contact_image").src = data.picture;
    document.querySelector("#contact_address").innerHTML = data.address;
    document.querySelector("#contact_phone").innerHTML = data.phone;
    document.querySelector("#contact_about").innerHTML = data.description;
    const contactFavorite = document.querySelector("#contact_favorite");
    if (data.favorite) {
      contactFavorite.innerHTML =
        "<i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i>";
    } else {
      contactFavorite.innerHTML = "Not Favorite Contact";
    }

    document.querySelector("#contact_website").href = data.websiteLink;
    document.querySelector("#contact_website").innerHTML = data.websiteLink;
    document.querySelector("#contact_linkedIn").href = data.linkedInLink;
    document.querySelector("#contact_linkedIn").innerHTML = data.linkedInLink;
    openContactModal();
  } catch (error) {
    console.log("Error: ", error);
  }
}
