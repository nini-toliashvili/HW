package com.example.test7.presentation.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.homework15.ViewPagerAdapter
import com.example.test7.databinding.HomeFragmentBinding
import com.example.test7.presentation.viewmodels.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentHome : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {
    private val viewModel: ViewModel by viewModels()
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPagerAdapter
    override fun setUp() {
        viewPager = binding.viewPager

//        viewPager.setPageTransformer { page, position ->
//            val scaleFactor = 0.75f + (1 - Math.abs(position)) * 0.25f
//            page.scaleX = scaleFactor
//            page.scaleY = scaleFactor
//            val offset = position * 0.3f
//            page.translationX = offset
//        }


    adapter = ViewPagerAdapter(emptyList())

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.items.collectLatest { cards ->
                if (cards.isNotEmpty()) {
                    adapter = ViewPagerAdapter(cards)
                    viewPager.adapter = adapter
                }
            }

        }
        viewModel.fetchItems()

        with(binding) {
            viewPager.adapter = adapter
            viewPager.offscreenPageLimit = 3
            viewPager.setPageTransformer { page, position ->
                val scaleFactor =
                    0.85f + (1 - Math.abs(position)) * 0.15f
                page.scaleY = scaleFactor
                page.alpha =
                    0.5f + (1 - Math.abs(position)) * 0.5f
                val pageMarginPx = -40
                when {
                    position < -1 -> {
                        page.translationX = -page.width.toFloat()
                    }

                    position <= 1 -> {
                        val offset = position * -(pageMarginPx)
                        page.translationX = offset
                    }

                    else -> {
                        page.translationX = page.width.toFloat()
                    }
                }
            }
        }

    }
}