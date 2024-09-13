package com.github.aakumykov.kotlin_playground

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.aakumykov.kotlin_playground.databinding.ActivityMainBinding
import com.github.aakumykov.kotlin_playground.extensions.showAppProperties
import com.github.aakumykov.kotlin_playground.extensions.showToast
import com.github.aakumykov.kotlin_playground.shortcuts_parser.ShortcutsHandler
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.RawShortcutResolver
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.ResourceResolver
import java.io.InputStream
import javax.xml.parsers.SAXParserFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.messages.observe(this) { messages -> binding.logView.text = messages.joinToString("\n") }
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appInfoButton.setOnClickListener { showAppProperties() }
        binding.clearLogButton.setOnClickListener { Logger.clear() }

        binding.button1.setOnClickListener { action1() }
        binding.button2.setOnClickListener { action2() }
        binding.button3.setOnClickListener { action3() }
        binding.button4.setOnClickListener { action4() }
    }


    private fun action1() {
        val factory = SAXParserFactory.newInstance()
        val saxParser = factory.newSAXParser()
        val baeldungHandler = BaeldungHandler()

        val xmlFileInputStream: InputStream = resources.openRawResource(R.raw.baeldung)
        saxParser.parse(xmlFileInputStream, baeldungHandler)

        val website = baeldungHandler.website
        Log.d(TAG, website.toString())
    }

    private fun action2() {
        val shortcutsParser = ShortcutsParser(
            ShortcutsXMLRawParser(SAXParserFactory.newInstance().newSAXParser(), ShortcutsHandler()),
            RawShortcutResolver(ResourceResolver(packageName, resources))
        )

        val shortcuts = shortcutsParser.parse(this, R.raw.shortcuts)

        shortcuts.getOrNull()?.also { list ->
            list.forEachIndexed { index, shortcut ->
                Logger.d(TAG, "$index) ${shortcut.shortcutId}")
            }
        } ?: {
            showToast("Список ярлыков пуст")
        }
    }

    private fun action3() {
        showToast("Привет 3")
    }

    private fun action4() {
        showToast("Привет 4")
    }



    private fun log(text:String) = Logger.d(TAG, text)


    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}