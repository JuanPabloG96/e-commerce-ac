export function cartCard(product) {
  return `
    <li class="flex justify-between items-center gap-5 mb-4 border-b pb-4">
      <img src="${product.img_src}" alt="${product.name} image"
         class="w-16 h-16 object-cover mr-4 rounded-md">
      <div class="w-full flex items-center justify-between">
        <h4 class="text-lg font-bold text-violet-500">${product.name}</h4>
        <p class="text-md text-gray-700 font-semibold">$${product.price}</p>
      </div>
      <button class="bg-red-500 text-white py-1 px-3 rounded hover:bg-red-600 transition"
         onclick="removeFromCart(${product.id})">
         Eliminar
      </button>
    </li>`;
}