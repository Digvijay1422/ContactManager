console.log("script.js")
document.addEventListener("DOMContentLoaded",()=>{

    changeTheme();
})

let cuu_theme = getTheme();


function changeTheme()
{
    document.querySelector("html").classList.add(cuu_theme);

    //Set the listner
    const changeThemeButton =  document.querySelector("#theme")
    changeThemeButton.addEventListener("click",(Event)=>{
        const oldTheme  = cuu_theme
        console.log("Change button");
        if(cuu_theme=="dark"){
            cuu_theme="light";
        }
        else{
            cuu_theme = "dark";
        }
        
        document.querySelector("html").classList.remove(oldTheme);
        //localstorage update
        setTheme(cuu_theme);
        document.querySelector("html").classList.add(cuu_theme);

        //Changing text
        changeThemeButton.querySelector("span").textContent = cuu_theme =="light"? "Dark":"Light";

    })
}

//set Theme
function setTheme(theme)
{
    localStorage.setItem("theme",theme)
}

//get theme
function getTheme()
{
    let theme=localStorage.getItem("theme")
    return theme?theme:"light";
}