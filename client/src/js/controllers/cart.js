import { cartCard } from "/e-commerce/client/src/js/components/cartCard.js";
import { loadTemplate, manageMenu, isUserAuthenticated } from "/e-commerce/client/src/js/utils/helpers.js";
import { header, footer, headerUrl, footerUrl, userHeaderUrl } from "/e-commerce/client/src/js/utils/constants.js";
import { fetchData } from "/e-commerce/client/src/js/services/apiService.js";

async function initApp() {
  if (isUserAuthenticated()) {
    await loadTemplate(userHeaderUrl, header);
  } else {
    await loadTemplate(headerUrl, header);
  }
  await loadTemplate(footerUrl, footer);
  manageMenu();
  await loadCart();

}

async function loadCart() {
  const cartContainer = document.getElementById('cart-container');
  const totalPrice = document.getElementById('total-price');

  try {
    const cart = await fetchData('cart', 'GET', null, 'Error al cargar el carrito');
    if (cart.items && cart.items.length > 0) {
      cartContainer.innerHTML = cart.items.map(item => cartCard(item)).join('');
      cartContainer.hidden = false;
      updateTotal(cart.items);
    } else {
      cartContainer.innerHTML = '<p class="text-center text-gray-500">No hay productos en el carrito</p>';
      cartContainer.hidden = false;
    }
  } catch (error) {
    console.error('Error loading cart:', error);
  }
}

function updateTotal(items) {
  const total = items.reduce((sum, item) => sum + (item.product.price * item.quantity), 0);
  document.getElementById('total-price').textContent = total.toFixed(2);
}

window.removeFromCart = async (cartItemId) => {
  try {
    await fetchData(`cart/items/${cartItemId}`, 'DELETE', null, 'Error al eliminar producto');
    const itemElement = document.querySelector(`[data-item-id="${cartItemId}"]`);
    if (itemElement) {
      itemElement.remove();
    }
    await loadCart();
  } catch (error) {
    console.error('Error removing item:', error);
  }
};


window.updateQuantity = async (cartItemId, change) => {
  try {
    const response = await fetchData(`cart/items/${cartItemId}`, 'PUT', {
      quantity_change: change
    }, 'Error al actualizar cantidad');

    if (response.success) {
      await loadCart();
    }
  } catch (error) {
    console.error('Error updating quantity:', error);
  }
};

document.addEventListener("DOMContentLoaded", initApp);
