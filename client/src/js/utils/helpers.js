export async function loadTemplate(templateUrl, container) {
  fetch(templateUrl)
    .then(response => response.text())
    .then(data => {
      container.innerHTML = data;
    })
    .catch(error => {
      console.error("Error loading template:", error);
    });
}

export function manageMenu() {
  window.toggleMenu = function () {
    const menu = document.getElementById("menu");
    menu.classList.toggle("hidden");
  }

  window.addEventListener("click", (event) => {
    if (!event.target.matches("#user-icon")) {
      const menu = document.getElementById("menu");
      menu.classList.add("hidden");
    }
  })
}