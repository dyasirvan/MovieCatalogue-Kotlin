package com.android.moviecatalogue.ui.favorite.tv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.moviecatalogue.R
import com.android.moviecatalogue.databinding.FragmentTvFavBinding
import com.android.moviecatalogue.ui.adapter.MovieTvAdapter
import com.android.moviecatalogue.utils.Constant
import com.android.moviecatalogue.utils.CustomMarginItemDecoration
import com.android.moviecatalogue.utils.SortUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvFavFragment : Fragment() {
    private lateinit var binding: FragmentTvFavBinding
    private val viewModel: TvFavViewModel by viewModels()
    private val tvAdapter = MovieTvAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null){
            showAllData()

            binding.btnSort.setOnClickListener {
                val dialog = BottomSheetDialog(requireContext())
                val viewBottomSheetDialog = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
                val btnClose = viewBottomSheetDialog.findViewById<ImageView>(R.id.btn_close)
                btnClose.setOnClickListener { dialog.dismiss() }
                val sortAsc = viewBottomSheetDialog.findViewById<RelativeLayout>(R.id.ascending)
                sortAsc.setOnClickListener {
                    dialog.dismiss()
                    sortByName(SortUtils.ASCENDING)
                }
                val sortDesc = viewBottomSheetDialog.findViewById<RelativeLayout>(R.id.descending)
                sortDesc.setOnClickListener {
                    dialog.dismiss()
                    sortByName(SortUtils.DESCENDING)
                }
                dialog.setCancelable(true)
                dialog.setContentView(viewBottomSheetDialog)
                dialog.show()
            }

            setupRecyclerView()
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvTvFav){
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(
                CustomMarginItemDecoration(
                    resources.getDimension(R.dimen.margin_16).toInt()
                )
            )
            setHasFixedSize(false)
            adapter = tvAdapter
        }
    }

    private fun sortByName(sort: String) {
        viewModel.sortByName(Constant.MOVIE, sort).observe(viewLifecycleOwner, { movies ->
            if(movies != null){
                tvAdapter.submitList(movies)
            }
        })
    }

    private fun showAllData() {
        viewModel.getTvFavorites(Constant.TV_SHOW).observe(viewLifecycleOwner, {tv ->
            if(tv != null){
                tvAdapter.submitList(tv)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTvFavBinding.inflate(inflater, container, false)
        return binding.root
    }

}