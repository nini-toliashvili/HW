package com.example.homework15


import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.homework15.databinding.FragmentPaymentBinding

class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate), OnItemClickListener {

    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var viewPager: ViewPager2
     lateinit var adapter: ViewPagerAdapter
    override fun setUp() {
        viewPager = binding.viewPager
        viewPager.setPageTransformer { page, position ->
            val scaleFactor = 0.75f + (1 - Math.abs(position)) * 0.25f
            page.scaleX = scaleFactor
            page.scaleY = scaleFactor
            val offset = position * 0.3f
            page.translationX = offset
        }
        val list = viewModel.currentCards.value!!
        adapter = ViewPagerAdapter(list, this)


        viewModel.currentCards.observe(this) { cards ->
            adapter.submitList(cards.toList())
        }

        viewPager.adapter = adapter
        adapter.submitList(list)
        listeners()
    }

    private fun listeners() {
    binding.addNew.setOnClickListener {
        findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToAddNewCardFragment())
    }


    }

    override fun onItemClick(position: Int) {
        val bottomsheet = BottomSheetDialog(position)
        bottomsheet.show(requireActivity().supportFragmentManager,bottomsheet.tag )


    }
}