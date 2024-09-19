package com.github.aakumykov.kotlin_playground

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView.CHOICE_MODE_MULTIPLE
import android.widget.AdapterView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import com.github.aakumykov.android_dynamic_shortcuts_manager.DefaultShortcutsCreator
import com.github.aakumykov.android_dynamic_shortcuts_manager.dynamic_shortcut_manager.DynamicShortcutManager
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.ShortcutsParser
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.RawShortcutResolver
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ResourceResolver
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsSAXHandler
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsXMLRawParser
import com.github.aakumykov.kotlin_playground.databinding.ActivityMainBinding
import com.github.aakumykov.kotlin_playground.extensions.getErrorMessage
import com.github.aakumykov.kotlin_playground.extensions.showToast
import com.google.android.material.snackbar.Snackbar
import javax.xml.parsers.SAXParserFactory


class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private val dynamicShortcutManager by lazy { DynamicShortcutManager(this, 4) }
    private val shortcutsParser: ShortcutsParser by lazy { ShortcutsParser.getDefault(packageName, resources) }

    private lateinit var binding: ActivityMainBinding

    private val initialShortcutList: List<Shortcut> by lazy {
        try {
            shortcutsParser.parse(R.raw.shortcuts)
        } catch (e: Exception) {
            showErrorMessage(e)
            emptyList()
        }
    }

    private val shortcutList: MutableList<Shortcut> by lazy { initialShortcutList.toMutableList() }

    private val checkedShortcutList: MutableList<Shortcut> = mutableListOf()
    private val uncheckedShortcutList: MutableList<Shortcut> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ShortcutArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, shortcutList)

        binding.listView.apply {
            this.adapter = adapter
            this.onItemClickListener = this@MainActivity
            this.choiceMode = CHOICE_MODE_MULTIPLE
        }

        binding.createDefaultShortcuts.setOnClickListener { createDefaultShortcuts() }
        binding.removeAllShortcuts.setOnClickListener { removeAllShortcuts() }
        binding.updateShortcutsButton.setOnClickListener { updateShortcutsAsSelected() }
    }


    private fun createDefaultShortcuts() {
        try {
            DefaultShortcutsCreator.getDefault(this).initShortcuts(R.raw.shortcuts)
            showSuccessMessage(R.string.default_shortcuts_are_crated)
        }
        catch (e: Exception) {
            showErrorMessage(e)
        }
    }


    private fun updateShortcutsAsSelected() {
        try {
            dynamicShortcutManager.createDynamicShortcuts(checkedShortcutList)
            dynamicShortcutManager.removeDynamicShortcuts(uncheckedShortcutList)
            showSuccessMessage(R.string.shortcuts_are_updated)

        } catch (e: Exception) {
            showErrorMessage(e)
        }
    }


    private fun removeAllShortcuts() {
        try {
            dynamicShortcutManager.removeDynamicShortcuts(initialShortcutList)
            showSuccessMessage(R.string.all_shortcuts_are_removed)
        } catch (e: Exception) {
            showErrorMessage(e)
        }
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        checkedShortcutList.clear()

        uncheckedShortcutList.clear()
        uncheckedShortcutList.addAll(shortcutList)

        binding.listView.checkedItemPositions.forEach { key, value ->
            if (value) {
                shortcutList[key].also {
                    checkedShortcutList.add(it)
                    uncheckedShortcutList.remove(it)
                }
            }
        }

        Log.d(TAG, "Устан. галочки: ${checkedShortcutList.map { it.shortcutId }.joinToString(", ")}")
        Log.d(TAG, "Снятые галочки: ${uncheckedShortcutList.map { it.shortcutId }.joinToString(", ")}")
    }



    private fun string(@StringRes stringRes: Int): String = this.getString(stringRes)


    private fun showSuccessMessage(@StringRes stringRes: Int) {
        Log.d(TAG, string(stringRes))
        showSnackbar(stringRes)
    }

    private fun showErrorMessage(t: Throwable) {
        Log.e(TAG, t.getErrorMessage(), t)
        showSnackbar(t.getErrorMessage())
    }

    private fun showSnackbar(@StringRes stringRes: Int) {
        showSnackbar(string(stringRes))
    }

    private fun showSnackbar(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).apply {
            setAction(R.string.snackbar_close) {}
        }.show()
    }


    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}



class ShortcutArrayAdapter(context: Context, itemLayoutRes: Int, initialShortcutList: MutableList<Shortcut>)
    : ListViewAdapter<Shortcut>(context, itemLayoutRes, android.R.id.text1, initialShortcutList)
{
    override fun getTitle(listItem: Shortcut?, itemPosition: Int): String {
        return listItem?.shortcutShortLabel?.let { context.getString(it) } ?: "no-title*"
    }
}