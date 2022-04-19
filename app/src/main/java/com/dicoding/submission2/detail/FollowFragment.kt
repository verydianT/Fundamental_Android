package com.dicoding.submission2.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dicoding.submission2.databinding.FragmentFollowBinding
import com.dicoding.submission2.model.RespondDataUser

class FollowFragment : Fragment() {
    private var position = 0
    private val bindingFragment: FragmentFollowBinding by viewBinding()
    private val detailViewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFollowBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.listDataUser.observe(viewLifecycleOwner) {
            listUser -> bindingFragment.rvFollow.apply {
                adapter = ViewAdapter(listUser as ArrayList<RespondDataUser>)
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireActivity())
            }
        }
        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLoading(it: Boolean?) {
        bindingFragment.progressBar.visibility = if (it!!) View.VISIBLE else View.GONE
    }

    companion object {
        const val Followers = 0
        const val Following = 1
        fun newInstance(param1: Int): FollowFragment =
            FollowFragment().apply {
                this.position = param1
            }
    }
}