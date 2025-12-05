package com.openclassrooms.arista.ui.exercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.arista.R
import com.openclassrooms.arista.domain.model.Exercise
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ExerciseAdapter(private val context: DeleteExerciseInterface) :
    ListAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        val localDateTime = exercise.startTime.atZone(ZoneId.systemDefault()).toLocalDateTime()
        holder.tvStartTime.text = "Start Time: ${localDateTime.format(formatter)}"
        holder.tvDuration.text = "Duration: ${exercise.duration} minutes"
        holder.tvCategory.text = "Category: ${exercise.category}"
        holder.tvIntensity.text = "Intensity: ${exercise.intensity}"
        holder.ivDelete.setOnClickListener { _: View? -> context.deleteExercise(exercise) }
    }

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvStartTime: TextView = itemView.findViewById(R.id.tv_start_time)
        val tvDuration: TextView = itemView.findViewById(R.id.tv_duration)
        val tvCategory: TextView = itemView.findViewById(R.id.tv_category)
        val tvIntensity: TextView = itemView.findViewById(R.id.tv_intensity)
        val ivDelete: ImageView = itemView.findViewById(R.id.delete)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Exercise> =
            object : DiffUtil.ItemCallback<Exercise>() {
                override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
                    return oldItem == newItem
                }
            }
    }


}
