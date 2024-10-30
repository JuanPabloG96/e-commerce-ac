export function productCard(product) {
  return `
    <div class="bg-white rounded-lg shadow-md overflow-hidden">
      <img src="${product.imagen}" alt="${product.nombre}" class="w-full h-48 object-cover">
      <div class="p-4">
        <h4 class="text-lg font-semibold text-violet-500 mb-2">${product.nombre}</h4>
        <p class="text-gray-600">$${product.precio}</p>
        <button class="mt-4 w-full bg-blue-500 text-white py-2 rounded hover:bg-violet-500 transition">
          Añadir al Carrito
        </button>
      </div>
    </div>
  `;
}