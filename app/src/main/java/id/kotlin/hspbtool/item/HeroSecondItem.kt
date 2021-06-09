package id.kotlin.hspbtool.item

import id.kotlin.hspbtool.R
import id.kotlin.hspbtool.domain.Hero
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_hero.view.imgHeroes
import kotlinx.android.synthetic.main.item_hero.view.txtHeroName

class HeroSecondItem(private val hero: Hero) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txtHeroName.text = hero.name
        Picasso.get().load(hero.image).into(viewHolder.itemView.imgHeroes)
    }

    override fun getLayout(): Int = R.layout.item_hero_second
}