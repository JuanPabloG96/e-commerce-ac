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