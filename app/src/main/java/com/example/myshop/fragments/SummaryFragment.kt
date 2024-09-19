package com.example.myshop.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myshop.Constants.NO_USER_FOUND
import com.example.myshop.Constants.PAYMENT_COD
import com.example.myshop.Constants.PAYMENT_MODE
import com.example.myshop.adapter.OrderSummaryAdapter
import com.example.myshop.databinding.FragmentSummaryBinding
import com.example.myshop.model.data.DeliveryAddress
import com.example.myshop.model.data.Item
import com.example.myshop.model.data.ItemX
import com.example.myshop.model.data.PlaceOrderRequest
import com.example.myshop.security.SharedPreferences
import com.example.myshop.viewModel.AddressViewModel
import com.example.myshop.viewModel.CartViewModel
import com.example.myshop.viewModel.SummaryViewModel

class SummaryFragment : Fragment() {
    private lateinit var binding: FragmentSummaryBinding
    private lateinit var summaryViewModel: SummaryViewModel
    private var cartDetails: MutableList<Item> = mutableListOf()  // Initialize the list here
    private lateinit var addressDetails: DeliveryAddress
    private lateinit var orderAdapter: OrderSummaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        summaryViewModel = ViewModelProvider(requireActivity())[SummaryViewModel::class.java]

        // Observe and retrieve data asynchronously
        observeSummaryDetails()
        displayOrderSummary()
    }

    @SuppressLint("SetTextI18n")
    private fun displayOrderSummary() {
        summaryViewModel.placeOrder.observe(viewLifecycleOwner) { order ->
            binding.textOrderId.text = order.orderId.toString()
            summaryViewModel.getOrderSummary(order.orderId.toString())
            summaryViewModel.summaryOrder.observe(viewLifecycleOwner) { orderDetails ->
                setUpRecyclerView(orderDetails.order.items)
                binding.apply {
                    textOrderId.text = "Order ID: ${orderDetails.order.order_id}"
                    textOrderStatus.text = "Order Status: RECEIVED"
                    textOrderDate.text = "Order Date: ${orderDetails.order.order_date}"
                    textAddressTitle.text = "Address Title: ${orderDetails.order.address_title}"
                    textAddress.text= "Address: ${orderDetails.order.address}"
                    textBillAmount.text = "Bill Amount: ${orderDetails.order.bill_amount}"
                    textPaymentMethod.text = "Payment Method: ${orderDetails.order.payment_method}"
                }
            }
        }
    }

    private fun observeSummaryDetails() {
        val cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]
        val addressViewModel = ViewModelProvider(requireActivity())[AddressViewModel::class.java]

        cartViewModel.cartData.observe(viewLifecycleOwner) { cartList ->
            cartDetails.clear()
            cartList.forEach { cart ->
                cartDetails.add(Item(cart.id, cart.quantity, cart.price.toInt()))
            }
            checkAndSubmitOrder(cartViewModel, addressViewModel)
        }

        addressViewModel.selectedAddress.observe(viewLifecycleOwner) { address ->
            addressDetails = DeliveryAddress(address.address, address.title)
            checkAndSubmitOrder(cartViewModel, addressViewModel)
        }
    }

    private fun checkAndSubmitOrder(
        cartViewModel: CartViewModel,
        addressViewModel: AddressViewModel
    ) {
        if (cartDetails.isNotEmpty() && ::addressDetails.isInitialized) {
            val userId = SharedPreferences.getString(SharedPreferences.KEY_USER_ID, NO_USER_FOUND)
            val totalPrice =
                cartViewModel.orderTotal.value ?: 0
            val paymentMethod = arguments?.getString(PAYMENT_MODE) ?: PAYMENT_COD

            val orderDetails = PlaceOrderRequest(
                totalPrice,
                addressDetails,
                cartDetails,
                paymentMethod,
                userId.toInt()
            )
            summaryViewModel.getOrderDetails(orderDetails)
        }
    }

    private fun setUpRecyclerView(items: List<ItemX>) {
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(requireContext())
        orderAdapter = OrderSummaryAdapter(items)
        binding.recyclerViewItems.adapter = orderAdapter
    }
}
