package com.zhmyr.task2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zhmyr.biblib.BibDatabase
import com.zhmyr.biblib.Types

class BibLibAdapter(val entriesNumber: Int, val database: BibDatabase) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return entriesNumber
    }

    override fun getItemViewType(position: Int): Int {
        return database.getEntry(position).type.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BibLibViewHolder<*> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (Types.values()[viewType]) {
            Types.ARTICLE -> {
                ArticleViewHolder(
                    layoutInflater.inflate(R.layout.article, parent, false)
                )
            }
            Types.MISC -> {
                MiscViewHolder(
                    layoutInflater.inflate(R.layout.misc, parent, false)
                )
            }
            Types.INPROCEEDINGS -> {
                InproceedingsViewHolder(
                    layoutInflater.inflate(R.layout.inproceedings, parent, false)
                )
            }
            Types.TECHREPORT -> {
                TechreportViewHolder(
                    layoutInflater.inflate(R.layout.techreport, parent, false)
                )
            }
            Types.BOOK -> {
                BookViewHolder(
                    layoutInflater.inflate(R.layout.book, parent, false)
                )
            }
            Types.INCOLLECTION -> {
                IncollectionViewHolder(
                    layoutInflater.inflate(R.layout.incollection, parent, false)
                )
            }
            Types.UNPUBLISHED -> {
                UnpublishedViewHolder(
                    layoutInflater.inflate(R.layout.unpublished, parent, false)
                )
            }
            else -> {
                throw IllegalArgumentException("Illegal Type")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entry = database.getEntry(position)

        when (entry.type) {
            Types.ARTICLE -> (holder as ArticleViewHolder).bind(entry)
            Types.MISC -> (holder as MiscViewHolder).bind(entry)
            Types.INPROCEEDINGS -> (holder as InproceedingsViewHolder).bind(entry)
            Types.TECHREPORT -> (holder as TechreportViewHolder).bind(entry)
            Types.BOOK -> (holder as BookViewHolder).bind(entry)
            Types.INCOLLECTION -> (holder as IncollectionViewHolder).bind(entry)
            Types.UNPUBLISHED -> (holder as UnpublishedViewHolder).bind(entry)
            else -> throw IllegalArgumentException("Illegal Type")
        }
    }
}
