export function orderCard(order) {
  return `
    <div class="bg-white border border-gray-200 rounded-lg p-4 shadow-md flex justify-between">
        <h2 class="text-lg font-semibold text-gray-800">Pedido #${order.order_id}</h2>
        <div class="flex justify-between items-center gap-20">
          <p class="text-sm text-gray-600">Fecha: ${order.created_at}</p>
          <p class="text-sm text-gray-600 font-bold">Total: $${order.total}</p>
        </div>
        <div class="flex flex-col items-center">
          <span class="text-sm font-semibold text-yellow-500">${order.status}</span>
          <a href="/e-commerce/client/src/view/order-details.html"
            class="mt-4 inline-block text-violet-500 hover:text-violet-600 font-semibold text-sm transition underline">
            Ver detalles
          </a>
        </div>
      </div>
  `;
}