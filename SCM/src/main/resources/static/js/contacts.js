// // import { Modal } from 'flowbite';


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