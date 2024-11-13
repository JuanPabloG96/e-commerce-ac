export function productCard(product) {
  return `
    <div class="bg-white rounded-lg shadow-md overflow-hidden">
      <img src="${product.img_src}" alt="${product.name}" class="w-full h-48 object-cover" loading="lazy">
      <div class="p-4">
        <h4 class="text-lg font-semibold text-violet-500 mb-2">${product.name}</h4>
        <p class="text-gray-600">$${product.price}</p>
        <button 
          id="add-to-cart-${product.product_id}"
          class="mt-4 w-full bg-blue-500 text-white py-2 rounded hover:bg-violet-500 transition"
          onclick="addToCart(${product.product_id})"
        >
          Añadir al Carrito
        </button>
      </div>
    </div>
  `;
}
