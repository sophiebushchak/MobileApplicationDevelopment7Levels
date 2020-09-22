package com.example.madlevel4task1.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task1.R
import com.example.madlevel4task1.model.Product
import com.example.madlevel4task1.repository.ProductRepository
import kotlinx.android.synthetic.main.fragment_shopping_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ShoppingListFragment : Fragment() {
    private val products = arrayListOf<Product>()
    private val productAdapter = ProductAdapter(products)
    private lateinit var productRepository: ProductRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productRepository = ProductRepository(requireContext())
        initViews()
    }

    private fun initViews() {
        rvProducts.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvProducts.adapter = productAdapter
        rvProducts.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        rvProducts.setHasFixedSize(true)
    }

    private fun getShoppingListFromDatabase() {
        mainScope.launch {
            val products = withContext(Dispatchers.IO) {
                productRepository.getAllProducts()
            }
            this@ShoppingListFragment.products.clear()
            this@ShoppingListFragment.products.addAll(products)
            this@ShoppingListFragment.productAdapter.notifyDataSetChanged()
        }
    }


}