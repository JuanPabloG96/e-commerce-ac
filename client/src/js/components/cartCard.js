export function cartCard(item) {
  return `
      <li class="flex justify-between items-center gap-5 mb-4 border-b pb-4">
          <img src="${item.product.img_src}" alt="${item.product.name} image"
              class="w-16 h-16 object-cover mr-4 rounded-md">
          <div class="w-full flex items-center justify-between">
              <h4 class="text-lg font-bold text-violet-500">${item.product.name}</h4>
              <div class="flex items-center gap-4">
                  <div class="flex items-center">
                      <button onclick="updateQuantity(${item.cart_items_id}, -1)" class="px-2 py-1 bg-gray-200 rounded-l">-</button>
                      <span class="px-4 py-1 bg-gray-100">${item.quantity}</span>
                      <button onclick="updateQuantity(${item.cart_items_id}, 1)" class="px-2 py-1 bg-gray-200 rounded-r">+</button>
                  </div>
                  <p class="text-md text-gray-700 font-semibold">$${(item.product.price * item.quantity).toFixed(2)}</p>
              </div>
          </div>
          <button 
              id="remove-item-${item.cart_items_id}"
              class="bg-red-500 text-white py-1 px-3 rounded hover:bg-red-600 transition"
              onclick="removeFromCart(${item.cart_items_id})"
          >
              Eliminar
          </button>
      </li>`;
}
