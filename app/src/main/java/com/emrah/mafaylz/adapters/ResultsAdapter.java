package com.emrah.mafaylz.adapters;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.emrah.mafaylz.R;
import com.emrah.mafaylz.databinding.ItemFileSearchResultBinding;
import com.emrah.mafaylz.model.FileSearchResult;
import com.emrah.mafaylz.model.FileSearchResultItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    List<FileSearchResultItem> files = new ArrayList<>();
    String query;

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                ItemFileSearchResultBinding.inflate(
                        LayoutInflater.from(parent.getContext())
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        FileSearchResultItem fileItem = files.get(position);
        holder.bind(fileItem, query);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public void setResult(FileSearchResult result) {
        this.files = result.getFileSearchResultList();
        this.query = result.getFileSearchQuery();
        notifyDataSetChanged();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        ItemFileSearchResultBinding binding;

        public ViewHolder(ItemFileSearchResultBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(FileSearchResultItem fileItem, String query) {
            String fileName = fileItem.getFileName();
            int matchPositionStartIndex = findMatchPositionStartIndex(fileName, query);
            Spannable fileNameWithQueryHighlight = prepareHighlightedSpannable(
                    fileName,
                    query,
                    matchPositionStartIndex
            );
            binding.tvFileName.setText(fileNameWithQueryHighlight);
        }

        private int findMatchPositionStartIndex(String fileName, String query) {
            String lowerCaseQuery = query.toLowerCase(Locale.getDefault());
            String lowerCaseFileName = fileName.toLowerCase(Locale.getDefault());
            return lowerCaseFileName.indexOf(lowerCaseQuery);
        }

        private Spannable prepareHighlightedSpannable(
                String fileName,
                String query,
                int matchPositionStartIndex
        ) {

            Spannable fileNameWithQueryHighlight = new SpannableStringBuilder(fileName);
            fileNameWithQueryHighlight.setSpan(
                    getHighlightSpanColor(),
                    matchPositionStartIndex,
                    matchPositionStartIndex + query.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            );
            return fileNameWithQueryHighlight;
        }

        private ForegroundColorSpan getHighlightSpanColor() {
            return new ForegroundColorSpan(
                    ContextCompat.getColor(
                            itemView.getContext(),
                            R.color.design_default_color_primary
                    )
            );
        }
    }
}
