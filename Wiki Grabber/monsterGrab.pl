##!/usr/bin/perl -w
use strict;
use HTML::Entities;
use LWP::UserAgent;
my($ua, $response, $mainContents, $thisLink, $thisContent);

$ua = LWP::UserAgent->new(
	protocols_allowed 	=> ['http', 'https'],
	timeout 		=> 10,
	agent 			=> "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0", #Necessary otherwise 403 forbidden.
);
$response = $ua->get('https://www.dandwiki.com/wiki/5e_Monsters');
if($response->is_success){
	$mainContents = $response->decoded_content;
}else{
	die $response->status_line;
}

my $count = 0;
while($mainContents =~ /<a href=\"(.*?)\"/g){
	$thisLink = $1;
	if($thisLink =~ /\(5e_Creature\)/){
		sleep(2);
		$response = $ua->get('https://www.dandwiki.com'.$thisLink);
		if($response->is_success){
			$thisContent = $response->decoded_content;
			if($thisLink =~ /wiki\/(.*?)_\(5e_Creature\)/){
				my @abilities	= ();
				my @features	= ();
				my @actions	= ();
				my @legendary 	= ();
				my @reactions	= ();
				open(DATA, ">$1.csv");
				#-----------------------------------------------------------------------------------------------------------------------
				if($thisContent =~/<p><i>(.*?)<\/i>/){print DATA "\"".rmHTML($1)."\",";}else{print DATA ",";}				#Size/Alignment
				if($thisContent =~/Armor Class<\/b>(.*?)(\d+)(\s?|\S?)/){print DATA "\"".$2."\",";}else{print DATA ",";}		#AC
				if($thisContent =~/Hit Points<\/a><\/b>(.*?)(\d+)(\s?|\S?)/){print DATA "\"".$2."\",";}else{print DATA ",";}		#HP
				if($thisContent =~/Speed<\/a><\/b>(.*?)(\n|<)/){print DATA "\"".rmCh($1)."\",\n";}else{print DATA ",\n";}		#Speed
				#-----------------------------------------------------------------------------------------------------------------------
				while($thisContent =~/\n<td>(.*?)\((.*?)\)/g){print DATA "\"".$1."\",";}						#Ability scores
				print DATA "\n";
				#-----------------------------------------------------------------------------------------------------------------------
				if($thisContent =~/Strength(.*?)<\/a> \+( \d+|\d+)/){print DATA "\"".$2."\",";}						#Str save	
				elsif($thisContent =~/Saving Throws<\/b>(.*?)(Str|Strength)(\s*?)\+(\s*?)(\d+)/){print DATA "\"".$5."\",";}
				else{print DATA ",";}
				if($thisContent =~/Dexterity(.*?)<\/a> \+( \d+|\d+)/){print DATA "\"".$2."\",";}					#Dex save
				elsif($thisContent =~/Saving Throws<\/b>(.*?)(Dex|Dexterity)(\s*?)\+(\s*?)(\d+)/){print DATA "\"".$5."\",";}
				else{print DATA ",";}
				if($thisContent =~/Constitution(.*?)<\/a> \+( \d+|\d+)/){print DATA "\"".$2."\",";}					#Con save
				elsif($thisContent =~/Saving Throws<\/b>(.*?)(Con|Constitution)(\s*?)\+(\s*?)(\d+)/){print DATA "\"".$5."\",";}
				else{print DATA ",";}
				if($thisContent =~/Intelligence(.*?)<\/a> \+( \d+|\d+)/){print DATA "\"".$2."\",";}					#Int save
				elsif($thisContent =~/Saving Throws<\/b>(.*?)(Int|Intelligence)(\s*?)\+(\s*?)(\d+)/){print DATA "\"".$5."\",";}
				else{print DATA ",";}
				if($thisContent =~/Wisdom(.*?)<\/a> \+( \d+|\d+)/){print DATA "\"".$2."\",";}						#Wis save
				elsif($thisContent =~/Saving Throws<\/b>(.*?)(Wis|Wisdom)(\s*?)\+(\s*?)(\d+)/){print DATA "\"".$5."\",";}
				else{print DATA ",";}
				if($thisContent =~/Charisma(.*?)<\/a> \+( \d+|\d+)/){print DATA "\"".$2."\",\n";}					#Cha save
				elsif($thisContent =~/Saving Throws<\/b>(.*?)(Cha|Charisma)(\s*?)\+(\s*?)(\d+)/){print DATA "\"".$5."\",\n";}
				else{print DATA ",\n";}
				#-----------------------------------------------------------------------------------------------------------------------
				if($thisContent =~/>(\s*?)Skills(.*?)((\s*?)|(.?))<b>/){print DATA "\"".rmHTML($2)."\",\n";}else{print DATA ",\n";}	#Skills
				#-----------------------------------------------------------------------------------------------------------------------
				if($thisContent =~/Vulnerabilities(\s*?)<\/a><\/b>(.*?)<br/){print DATA "\"".rmCh($2)."\",\n";}else{print DATA ",\n";}	#Damage Vulnerabilities
				#-----------------------------------------------------------------------------------------------------------------------
				if($thisContent =~/Resistances(\s*?)<\/a><\/b>(.*?)<br/){print DATA "\"".rmCh($2)."\",\n";}else{print DATA ",\n";}	#Damage Resistances
				#-----------------------------------------------------------------------------------------------------------------------
				if($thisContent =~/(Dmg |Damage )Imm(.*?)<\/b>(.*?)<br/){print DATA "\"".rmCh($3)."\",\n";}else{print DATA ",\n";}	#Damage Immunities
				#-----------------------------------------------------------------------------------------------------------------------
				if($thisContent =~/(Con |Condition )Imm(.*?)<\/b>(.*?)<br/){print DATA "\"".rmHTML($3)."\",\n";}else{print DATA ",\n";}	#Condition Immunities
				#-----------------------------------------------------------------------------------------------------------------------
				if($thisContent =~/Senses(.*?)<\/b>(.*?)(\s*?)<br/){print DATA "\"".rmHTML($2)."\",\n";}else{print DATA ",\n";}		#Senses
				#-----------------------------------------------------------------------------------------------------------------------
				if($thisContent =~/Languages(.*?)<\/b>(.*?)(\s*?)<br/){print DATA "\"".rmCh($2)."\",\n"}else{print DATA ",\n";}		#Languages
				#-----------------------------------------------------------------------------------------------------------------------
				if($thisContent =~/Challenge(\s*?)<\/b>(.*?)(\s*?)<br/){print DATA "\"".rmCh($2)."\",\n"}else{print DATA ",\n";}	#Challenge
				#-----------------------------------------------------------------------------------------------------------------------
				my $temp = "";														#Grab all Abilities/Features
				while($thisContent =~/<p><i><b>((.|\s)*?)<\/p>/g){#Good ones
					$temp = $1;
					chomp($temp);
					push(@abilities, $temp);
				}
				$temp = "";
				while(shorten($thisContent) =~/ACTION((.|\s)*?)\Q$temp\E((.|\s)*?)<b>(.+)(\s*)(<b>|<\/p>)((.|\s)+)/g){#Stupid ones
					$temp = $5;
					if(not grep(/\Q$temp\E/, @abilities)){
						push(@abilities, $temp);
					}
				}
				#-----------------------------------------------------------------------------------------------------------------------
				foreach my $i (@abilities){												#Sort all Abilities/Features
					if($thisContent =~/<\/p>(\s*)<\/td>(\s*)<td((.|\s)+)\Q$i\E/){	
						#Then it's flavor text beneath a picture. Don't include.
						#Not convinced that this will always work.
					}elsif($thisContent =~/LEGENDARY ACTIONS((.|\s)+)\Q$i\E/){
						push(@legendary, rmHTML($i));
					}elsif($thisContent =~/REACTIONS((.|\s)+)\Q$i\E/){
						push(@reactions, rmHTML($i));
					}elsif($thisContent =~/ACTIONS((.|\s)+)\Q$i\E/){
						push(@actions, rmHTML($i));
					}else{
						push(@features, rmHTML($i));
					}
				}
				#-----------------------------------------------------------------------------------------------------------------------
				foreach my $i (@features){print DATA "\"".$i."\",";}									#Features
				print DATA "\n";
				#-----------------------------------------------------------------------------------------------------------------------
				foreach my $i (@actions){print DATA "\"".$i."\",";}									#Actions
				print DATA "\n";
				#-----------------------------------------------------------------------------------------------------------------------
				foreach my $i (@reactions){print DATA "\"".$i."\",";}									#Reactions
				print DATA "\n";
				#-----------------------------------------------------------------------------------------------------------------------
				foreach my $i (@legendary){print DATA "\"".$i."\",";}									#Legendary Actions
				print DATA "\n";
				#-----------------------------------------------------------------------------------------------------------------------
				close(DATA);
				#last;
			}
		}else{
			die $response->status_line;
		}
	}
}

sub rmHTML{
	my $newString = $_[0];
	$newString =~s/<(.*?)>//g;
	$newString =~s/<(.*)//g;
	$newString =~s/(.*)>//g;
	return rmCh($newString);
}
sub rmCh{
	my $newString = $_[0];
	$newString =~s/,/;/g;
	$newString =~s/"/'/g;
	$newString =~s/[^[:ascii:]]+//g;
	return decode_entities($newString);
}
sub shorten{
	my $content = $_[0];
	$content =~s/((.|\s)+)Challenge(\s*?)<\/b>(.*?)(\s*?)<br//;
	$content =~s/<li ((.|\s)+)//g;
	$content =~s/<p><i><b>((.|\s)*?)<\/p>//g;
	$content =~s/<(.*?)div(.*?)>//g;
	$content =~s/<(.*?)table(.*?)>//g;
	$content =~s/<(.*?)span(.*?)>//g;
	return $content;
}
