package com.example.flowtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.flowtest.databinding.FragmentFirstBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (viewModel.progressbarShown) {
                        viewModel.cancelNavigation()
                        showProgress(false)
                    } else {
                        this.isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            })


        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (viewModel.progressbarShown) progressBar.visibility = View.VISIBLE
            buttonFirst.setOnClickListener {
                showProgress(true)
                viewModel.buttonPressed()
            }
        }

        viewModel.eventChannel.observe(viewLifecycleOwner) { delay ->
            if (delay > 0) {
                showProgress(true, delay)
            } else {
                showProgress(false)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }

    }


    private fun showProgress(show: Boolean, delay: Int = 0) {
        viewModel.progressbarShown = show
        with(binding) {
            progressCounter.text = delay.toString()
            if (show){ progress.visibility = View.VISIBLE }else {
                progress.visibility =
                    View.GONE
                progressCounter.text = ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun <T> Flow<T>.observe(viewLifecycleOwner: LifecycleOwner, onEvent: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                collect {
                    onEvent.invoke(it)
                }
            }
        }
    }
}
