package com.example.homework15

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.homework15.databinding.FragmentAddNewCardBinding
import java.util.UUID

class AddNewCardFragment : BaseFragment<FragmentAddNewCardBinding>(FragmentAddNewCardBinding::inflate) {



    private val viewModel: SharedViewModel by activityViewModels()

    override fun setUp() {
    listeners()

    }

    private fun listeners() {
        binding.backButton.setOnClickListener{
            findNavController().navigate(AddNewCardFragmentDirections.actionAddNewCardFragmentToPaymentFragment())
        }

        binding.addCardButton.setOnClickListener {


        if(validInput()) {
            val cardholderName = binding.etCardHolderName.text.toString()
            val formatted = StringBuilder()

            for (i in cardholderName.indices) {
                if ( i != 0 && i % 4 == 0) {
                    formatted.append("  ")
                }
                formatted.append(cardholderName[i])
            }
            val cardNumber = binding.etCardNumber.text.toString()


            val date = binding.etExpireDate.text.toString()
            val cvv = binding.etCvv.text.toString().toInt()
            val type = binding.visaRadioButton.isChecked
            val newCard = Card(UUID.randomUUID(),formatted.toString(), cardNumber, date, cvv, type )
            viewModel.addCard(newCard)
        }
        }
    }

    private fun validInput():Boolean {
        val cardholderName = binding.etCardHolderName.text.toString()
        val cardNumber = binding.etCardNumber.text.toString()
        val date = binding.etExpireDate.text.toString()
        val cvv = binding.etCvv.text.toString()

        if (cardholderName == "") {
            binding.etCardHolderName.error = "field should not be empty"
            return false
        }
        if (cardNumber == "") {
            binding.etCardNumber.error = "field should not be empty"
            return false
        }
        else if (cardNumber.length != 16 || cardNumber.contains(Regex("[^0-9]+"))) {
            binding.etCardNumber.error = "please, provide valid card number"
            return false
        }
        if (cvv == "") {
            binding.etCvv.error = "field should not be empty"
            return false
        } else if (cvv.length != 3) {
            binding.etCvv.error = "please, provide valid cvv"
            return false
        }
        if (date == "") {
            binding.etCvv.error = "field should not be empty"
            return false
        }else if (date.length != 4) {
            binding.etExpireDate.error = "please, provide valid date"
            return false
        }

        return true
    }

}