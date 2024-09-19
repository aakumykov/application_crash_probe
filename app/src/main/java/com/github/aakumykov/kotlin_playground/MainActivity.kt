package com.github.aakumykov.kotlin_playground

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.CHOICE_MODE_MULTIPLE
import android.widget.AdapterView
import android.widget.CheckBox
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import com.github.aakumykov.android_dynamic_shortcuts_manager.DefaultShortcutsCreator
import com.github.aakumykov.android_dynamic_shortcuts_manager.dynamic_shortcut_manager.DynamicShortcutManager
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.ShortcutsParser
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut
import com.github.aakumykov.kotlin_playground.databinding.ActivityMainBinding
import com.github.aakumykov.kotlin_playground.extensions.getErrorMessage
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private val dynamicShortcutManager by lazy { DynamicShortcutManager(this, 4) }
    private val shortcutsParser: ShortcutsParser by lazy { ShortcutsParser.getDefault(packageName, resources) }

    private lateinit var binding: ActivityMainBinding

    private lateinit var listAdapter: ShortcutArrayAdapter

    private val initialShortcutList: List<Shortcut> by lazy {
        try {
            shortcutsParser.parse(R.raw.shortcuts)
        } catch (e: Exception) {
            showErrorMessage(e)
            emptyList()
        }
    }

    private val shortcutList: MutableList<MutableShortcut> by lazy {
        initialShortcutList
            .map { MutableShortcut(it, it.enabled) }
            .toMutableList()
    }

    private val checkedShortcutList: MutableList<MutableShortcut> = mutableListOf()
    private val uncheckedShortcutList: MutableList<MutableShortcut> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listAdapter = ShortcutArrayAdapter(
            this,
            android.R.layout.simple_list_item_multiple_choice,
            shortcutList
        )

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

            listAdapter.setList(shortcutList)
            listAdapter.notifyDataSetChanged()
        }
        catch (e: Exception) {
            showErrorMessage(e)
        }
    }


    private fun updateShortcutsAsSelected() {

        displayShortcutsCheckState()

        try {
            dynamicShortcutManager.createDynamicShortcuts(checkedShortcutList.map { it.shortcut })
            dynamicShortcutManager.removeDynamicShortcuts(uncheckedShortcutList.map { it.shortcut})
            showSuccessMessage(R.string.shortcuts_are_updated)

        } catch (e: Exception) {
            showErrorMessage(e)
        }
    }


    private fun displayShortcutsCheckState() {
        shortcutList.clear()

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
        uncheckedShortcutList.addAll(shortcutList.onEach { it.isEnabled = false })

        binding.listView.checkedItemPositions.forEach { key, value ->
            if (value) {
                shortcutList[key].also { shortcut: MutableShortcut ->
                    checkedShortcutList.add(shortcut.apply { isEnabled = true })
                    uncheckedShortcutList.remove(shortcut)
                }
            }
        }

        Log.d(TAG, "Устан. галочки: ${checkedShortcutList.map { it.shortcut.shortcutId }.joinToString(", ")}")
        Log.d(TAG, "Снятые галочки: ${uncheckedShortcutList.map { it.shortcut.shortcutId }.joinToString(", ")}")
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



class ShortcutArrayAdapter(context: Context, itemLayoutRes: Int, initialShortcutList: MutableList<MutableShortcut>)
    : ListViewAdapter<MutableShortcut>(context, itemLayoutRes, android.R.id.text1, initialShortcutList)
{
    override fun getTitle(listItem: MutableShortcut?, itemPosition: Int): String {
        return listItem?.shortcut?.shortcutShortLabel?.let { context.getString(it) } ?: "no-title*"
    }

    /*override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val mutableShortcut: MutableShortcut? = getItem(position)

        return super.getView(position, convertView, parent).apply {
            findViewById<CheckBox>(android.R.id.checkbox)?.also { checkBox ->
                checkBox.isChecked = mutableShortcut?.isEnabled ?: false
            }
        }
    }*/
}


class MutableShortcut(val shortcut: Shortcut, var isEnabled: Boolean) {
    override fun toString(): String {
        return MutableShortcut::class.java.simpleName + " { ${shortcut.shortcutId}, $isEnabled }"
    }
}