package com.farminikagroup.farminika.data.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.model.Message
import com.farminikagroup.farminika.databinding.DeleteLayoutBinding
import com.farminikagroup.farminika.databinding.ReceiveMessageBinding
import com.farminikagroup.farminika.databinding.SendMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MessageAdapter(
    var context: Context,
    message: ArrayList<Message>?,
    senderRoom: String,
    receiverRoom: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var messages: ArrayList<Message>
    val ITEM_SENT = 1
    val ITEM_RECEIVE = 2
    var senderRoom: String
    var receiverRoom: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_SENT) {
            val view = LayoutInflater.from(context).inflate(R.layout.send_message, parent, false)
            SendMessageHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.receive_message, parent, false)
            ReceiveMessageHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val messages = messages[position]
        return if (FirebaseAuth.getInstance().uid == messages.senderId) {
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder.javaClass == SendMessageHolder::class.java) {
            val viewHolder = holder as SendMessageHolder
            if (message.message.equals("photo")) {
                viewHolder.binding.imageMessage.visibility = View.VISIBLE
                viewHolder.binding.message.visibility = View.GONE
                viewHolder.binding.linearLayoutMessage.visibility = View.GONE
                Glide.with(context).load(message.imageUrl)
                    .placeholder(R.drawable.home)
                    .into(viewHolder.binding.imageMessage)

            }
            viewHolder.binding.message.text = message.message
            viewHolder.itemView.setOnClickListener {
                val view = LayoutInflater.from(context).inflate(R.layout.delete_layout, null)
                val binding = DeleteLayoutBinding.bind(view)
                val dialog = AlertDialog.Builder(context).setTitle("Delete Message")
                    .setView(binding.root)
                    .create()
                binding.deleteForEveryOne.setOnClickListener {
                    message.message = "This message is removed"
                    message.messageId?.let {
                        FirebaseDatabase.getInstance().reference.child("Chats")
                            .child(senderRoom)
                            .child("message")
                            .child(it).setValue(message)
                    }
                    message.messageId?.let {it1 ->
                        FirebaseDatabase.getInstance().reference.child("Chats")
                            .child(receiverRoom)
                            .child("message")
                            .child(it1).setValue(message)
                    }
                    dialog.dismiss()
                }
                binding.deleteForME.setOnClickListener {
                    message.messageId?.let {
                        FirebaseDatabase.getInstance().reference.child("Chats")
                            .child(senderRoom)
                            .child("message")
                            .child(it).setValue(null)
                    }
                    dialog.dismiss()
                }
                binding.cancel.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
            }
        } else {
            val viewHolder = holder as ReceiveMessageHolder
            if (message.message.equals("photo")) {
                viewHolder.binding.imageMessage.visibility = View.VISIBLE
                viewHolder.binding.message.visibility = View.GONE
                viewHolder.binding.linearLayoutMessage.visibility = View.GONE
                Glide.with(context).load(message.imageUrl)
                    .placeholder(R.drawable.home)
                    .into(viewHolder.binding.imageMessage)

            }
            viewHolder.binding.message.text = message.message
            viewHolder.itemView.setOnClickListener {
                val view = LayoutInflater.from(context).inflate(R.layout.delete_layout, null)
                val binding = DeleteLayoutBinding.bind(view)
                val dialog = AlertDialog.Builder(context).setTitle("Delete Message")
                    .setView(binding.root)
                    .create()
                binding.deleteForEveryOne.setOnClickListener {
                    message.message = "This message is removed"
                    message.messageId?.let {
                        FirebaseDatabase.getInstance().reference.child("Chats")
                            .child(senderRoom)
                            .child("message")
                            .child(it).setValue(message)
                    }
                    message.messageId?.let {it1 ->
                        FirebaseDatabase.getInstance().reference.child("Chats")
                            .child(receiverRoom)
                            .child("message")
                            .child(it1).setValue(message)
                    }
                    dialog.dismiss()
                }
                binding.deleteForME.setOnClickListener {
                    message.messageId?.let {
                        FirebaseDatabase.getInstance().reference.child("Chats")
                            .child(senderRoom)
                            .child("message")
                            .child(it).setValue(null)
                    }
                    dialog.dismiss()
                }
                binding.cancel.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    inner class SendMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: SendMessageBinding = SendMessageBinding.bind(itemView)
    }

    inner class ReceiveMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ReceiveMessageBinding = ReceiveMessageBinding.bind(itemView)
    }

    init {
        this.messages = messages
        this.senderRoom = senderRoom
        this.receiverRoom = receiverRoom
    }
}